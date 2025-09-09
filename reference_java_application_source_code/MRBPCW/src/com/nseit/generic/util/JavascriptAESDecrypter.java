package com.nseit.generic.util;

import java.util.Base64;

public class JavascriptAESDecrypter
{
	public static String decrypt(String ciphertext, String password, int nBits)
			throws Exception
	{
		int blockSize = 16; // block size fixed at 16 bytes / 128 bits (Nb=4)
							// for AES
		if (!(nBits == 128 || nBits == 192 || nBits == 256))
			return ""; // standard allows 128/192/256 bit keys

		byte[] pBytes = password.getBytes("UTF-8");
		short[] passwordShorts = new short[pBytes.length];
		for (int i = 0; i < pBytes.length; i++)
		{
			passwordShorts[i] = pBytes[i];
			if (passwordShorts[i] < 0)
			{
				passwordShorts[i] += 256;
			}
		}

//		BASE64Decoder dec = new BASE64Decoder();
//		byte[] decodedCipher = dec.decodeBuffer(ciphertext);
		byte[] decodedCipher = Base64.getDecoder().decode(ciphertext);

		short[] decoded = new short[decodedCipher.length];
		for (int i = 0; i < decodedCipher.length; i++)
		{
			decoded[i] = decodedCipher[i] < 0 ? (short) (decodedCipher[i] + 256)
					: decodedCipher[i];
		}

		// use AES to encrypt password (mirroring encrypt routine)
		int nBytes = nBits / 8; // no bytes in key
		short[] pwBytes = new short[nBytes];
		for (int i = 0; i < nBytes; i++)
		{
			short sh = 0;
			if (i < passwordShorts.length)
			{
				sh = passwordShorts[i];
			}
			pwBytes[i] = sh;
		}

		short[] keyExpanded = keyExpansion(pwBytes);
		short[] state = cipher(pwBytes, keyExpanded);
		short[] sliced = slice(state, nBytes);
		state = concat(state, sliced);

		// recover nonce from 1st 8 bytes of ciphertext
		short[] counterBlock = new short[8];
		for (int i = 0; i < 8; i++)
		{
			counterBlock[i] = decoded[i];
			if (counterBlock[i] < 0)
			{
				counterBlock[i] += 256;
			}
		}

		// generate key schedule
		short[] keySchedule = keyExpansion(state);

		// separate ciphertext into blocks (skipping past initial 8 bytes)
		int nBlocks = (int) Math.ceil((decoded.length - 8.) / blockSize);
		String[] ct = new String[nBlocks];
		for (int b = 0; b < nBlocks; b++)
		{
			StringBuffer sb = new StringBuffer();
			for (int ind = 8 + b * blockSize; ind < 8 + b * blockSize
					+ blockSize; ind++)
			{
				if (ind < decoded.length)
				{
					short s = decoded[ind];
					if (s < 0)
					{
						s += 256;
					}
					sb.append((char) s);
				}
			}
			ct[b] = sb.toString();
		}

		// plaintext will get generated block-by-block into array of
		// block-length strings
		String[] plaintxt = new String[ct.length];

		short[] counterBlockTmp = new short[16];
		for (int b = 0; b < nBlocks; b++)
		{
			for (int c = 0; c < counterBlock.length; c++)
			{
				counterBlockTmp[c] = counterBlock[c];
			}
			// set counter (block #) in last 8 bytes of counter block (leaving
			// nonce in 1st 8 bytes)
			for (int c = 0; c < 4; c++)
			{
				counterBlockTmp[15 - c] = (short) (((b) >>> c * 8) & 0xff);
				if (counterBlockTmp[15 - c] == 255)
				{
					counterBlockTmp[15 - c] = 0;
				}
			}
			for (int c = 0; c < 4; c++)
			{
				counterBlockTmp[15 - c - 4] = (short) ((((b + 1) / 512 - 1) >>> c * 8) & 0xff);
				if (counterBlockTmp[15 - c - 4] == 255)
				{
					counterBlockTmp[15 - c - 4] = 0;
				}
			}

			short[] cipherCntr = cipher(counterBlockTmp, keySchedule); // encrypt
																		// counter
																		// block

			short[] plaintxtShort = new short[ct[b].length()];

			for (int i = 0; i < ct[b].length(); i++)
			{
				// -- xor plaintxt with ciphered counter byte-by-byte --
				plaintxtShort[i] = (short) (cipherCntr[i] ^ ct[b].charAt(i));
			}

			StringBuffer plaintext = new StringBuffer();
			for (int i = 0; i < plaintxtShort.length; i++)
			{
				if (plaintxtShort[i] > 194)
				{
					plaintext
							.append((char) ((plaintxtShort[i] - 194) * 64 + plaintxtShort[i + 1]));
					i++;
				}
				else
				{
					plaintext.append((char) plaintxtShort[i]);
				}
			}

			plaintxt[b] = plaintext.toString();
		}
		StringBuffer decrypted = new StringBuffer();
		for (String s : plaintxt)
		{
			decrypted.append(s);
		}
		return decrypted.toString();
	}

	private static short[] keyExpansion(short[] key)
	{ // generate Key Schedule (byte-array Nr+1 x Nb) from Key [§5.2]

		int Nb = 4; // block size (in words): no of columns in state (fixed at 4
					// for AES)
		int Nk = key.length / 4; // key length (in words): 4/6/8 for
									// 128/192/256-bit keys
		int Nr = Nk + 6; // no of rounds: 10/12/14 for 128/192/256-bit keys

		short[] w = new short[Nb * (Nr + 1) * 4];
		short[] temp = new short[4];

		for (int i = 0; i < Nk; i++)
		{
			for (int t = 0; t < 4; t++)
			{
				w[4 * i + t] = key[4 * i + t];
			}
		}

		for (int i = Nk; i < Nb * (Nr + 1); i++)
		{
			for (int t = 0; t < 4; t++)
			{
				temp[t] = w[4 * (i - 1) + t];
			}
			if (i % Nk == 0)
			{
				temp = subWord(rotWord(temp));
				for (int t = 0; t < 4; t++)
				{
					temp[t] ^= convertHexToShort(rCon[(4 * i / Nk) + t]);
				}
			}
			else if (Nk > 6 && i % Nk == 4)
			{
				temp = subWord(temp);
			}
			for (int t = 0; t < 4; t++)
			{
				w[4 * i + t] = (byte) (w[4 * (i - Nk) + t] ^ temp[t]);
				if (w[4 * i + t] < 0)
				{
					w[4 * i + t] += 256;
				}
			}
		}

		return w;
	}

	private static short[] subWord(short[] w)
	{ // apply SBox to 4-byte word w
		for (int i = 0; i < 4; i++)
		{
			w[i] = convertHexToShort(sBox[w[i]]);
		}
		return w;
	}

	private static short[] rotWord(short[] w)
	{ // rotate 4-byte word w left by one byte
		short tmp = w[0];
		for (int i = 0; i < 3; i++)
			w[i] = w[i + 1];
		w[3] = tmp;
		return w;
	}

	private static final String[] sBox = { "0x63", "0x7c", "0x77", "0x7b",
			"0xf2", "0x6b", "0x6f", "0xc5", "0x30", "0x01", "0x67", "0x2b",
			"0xfe", "0xd7", "0xab", "0x76", "0xca", "0x82", "0xc9", "0x7d",
			"0xfa", "0x59", "0x47", "0xf0", "0xad", "0xd4", "0xa2", "0xaf",
			"0x9c", "0xa4", "0x72", "0xc0", "0xb7", "0xfd", "0x93", "0x26",
			"0x36", "0x3f", "0xf7", "0xcc", "0x34", "0xa5", "0xe5", "0xf1",
			"0x71", "0xd8", "0x31", "0x15", "0x04", "0xc7", "0x23", "0xc3",
			"0x18", "0x96", "0x05", "0x9a", "0x07", "0x12", "0x80", "0xe2",
			"0xeb", "0x27", "0xb2", "0x75", "0x09", "0x83", "0x2c", "0x1a",
			"0x1b", "0x6e", "0x5a", "0xa0", "0x52", "0x3b", "0xd6", "0xb3",
			"0x29", "0xe3", "0x2f", "0x84", "0x53", "0xd1", "0x00", "0xed",
			"0x20", "0xfc", "0xb1", "0x5b", "0x6a", "0xcb", "0xbe", "0x39",
			"0x4a", "0x4c", "0x58", "0xcf", "0xd0", "0xef", "0xaa", "0xfb",
			"0x43", "0x4d", "0x33", "0x85", "0x45", "0xf9", "0x02", "0x7f",
			"0x50", "0x3c", "0x9f", "0xa8", "0x51", "0xa3", "0x40", "0x8f",
			"0x92", "0x9d", "0x38", "0xf5", "0xbc", "0xb6", "0xda", "0x21",
			"0x10", "0xff", "0xf3", "0xd2", "0xcd", "0x0c", "0x13", "0xec",
			"0x5f", "0x97", "0x44", "0x17", "0xc4", "0xa7", "0x7e", "0x3d",
			"0x64", "0x5d", "0x19", "0x73", "0x60", "0x81", "0x4f", "0xdc",
			"0x22", "0x2a", "0x90", "0x88", "0x46", "0xee", "0xb8", "0x14",
			"0xde", "0x5e", "0x0b", "0xdb", "0xe0", "0x32", "0x3a", "0x0a",
			"0x49", "0x06", "0x24", "0x5c", "0xc2", "0xd3", "0xac", "0x62",
			"0x91", "0x95", "0xe4", "0x79", "0xe7", "0xc8", "0x37", "0x6d",
			"0x8d", "0xd5", "0x4e", "0xa9", "0x6c", "0x56", "0xf4", "0xea",
			"0x65", "0x7a", "0xae", "0x08", "0xba", "0x78", "0x25", "0x2e",
			"0x1c", "0xa6", "0xb4", "0xc6", "0xe8", "0xdd", "0x74", "0x1f",
			"0x4b", "0xbd", "0x8b", "0x8a", "0x70", "0x3e", "0xb5", "0x66",
			"0x48", "0x03", "0xf6", "0x0e", "0x61", "0x35", "0x57", "0xb9",
			"0x86", "0xc1", "0x1d", "0x9e", "0xe1", "0xf8", "0x98", "0x11",
			"0x69", "0xd9", "0x8e", "0x94", "0x9b", "0x1e", "0x87", "0xe9",
			"0xce", "0x55", "0x28", "0xdf", "0x8c", "0xa1", "0x89", "0x0d",
			"0xbf", "0xe6", "0x42", "0x68", "0x41", "0x99", "0x2d", "0x0f",
			"0xb0", "0x54", "0xbb", "0x16" };

	private static final String HEX = "0123456789abcdef";

	private static short convertHexToShort(String hex)
	{
		short b = 0;
		if (hex != null && hex.length() == 4 && hex.startsWith("0x")
				&& HEX.indexOf(hex.charAt(2)) > -1
				&& HEX.indexOf(hex.charAt(3)) > -1)
		{
			char first = hex.charAt(2);
			char second = hex.charAt(3);
			b = (short) (16 * convertCharToByte(first) + convertCharToByte(second));
		}
		return b;
	}

	private static byte convertCharToByte(char c)
	{
		return (c >= 48 && c <= 57) ? (byte) (((int) c) - 48)
				: (byte) (((int) c) - 97 + 10);
	}

	// rCon is Round Constant used for the Key Expansion [1st col is 2^(r-1) in
	// GF(2^8)] [§5.2]
	private static final String[] rCon = { "0x00", "0x00", "0x00", "0x00",
			"0x01", "0x00", "0x00", "0x00", "0x02", "0x00", "0x00", "0x00",
			"0x04", "0x00", "0x00", "0x00", "0x08", "0x00", "0x00", "0x00",
			"0x10", "0x00", "0x00", "0x00", "0x20", "0x00", "0x00", "0x00",
			"0x40", "0x00", "0x00", "0x00", "0x80", "0x00", "0x00", "0x00",
			"0x1b", "0x00", "0x00", "0x00", "0x36", "0x00", "0x00", "0x00" };

	private static short[] cipher(short[] input, short[] w)
	{ // main Cipher function [§5.1]
		int Nb = 4; // block size (in words): no of columns in state (fixed at 4
					// for AES)
		int Nr = (w.length / Nb - 1) / 4; // no of rounds: 10/12/14 for
											// 128/192/256-bit keys

		// initialise 4xNb byte-array 'state' with input [§3.4]
		short[] state = new short[4 * Nb];
		for (int i = 0; i < 4 * Nb; i++)
		{
			state[(i % 4) * 4 + (int) Math.floor(i / 4)] = input[i];
		}

		state = addRoundKey(state, w, 0, Nb);

		for (int round = 1; round < Nr; round++)
		{
			state = subBytes(state, Nb);
			state = shiftRows(state, Nb);
			state = mixColumns(state, Nb);
			state = addRoundKey(state, w, round, Nb);
		}

		state = subBytes(state, Nb);
		state = shiftRows(state, Nb);
		state = addRoundKey(state, w, Nr, Nb);

		short[] output = new short[4 * Nb]; // convert state to 1-d array before
											// returning [§3.4]
		for (int i = 0; i < 4 * Nb; i++)
		{
			output[i] = state[i % 4 * 4 + (int) (Math.floor(i / 4))];
		}
		return output;
	}

	private static short[] addRoundKey(short[] state, short[] w, int rnd, int Nb)
	{ // xor Round Key into state S [§5.1.4]
		for (int r = 0; r < 4; r++)
		{
			for (int c = 0; c < Nb; c++)
				state[r * 4 + c] ^= w[(rnd * 4 + c) * 4 + r];
		}
		return state;
	}

	private static short[] subBytes(short[] s, int Nb)
	{ // apply SBox to state S [§5.1.1]
		for (int r = 0; r < 4; r++)
		{
			for (int c = 0; c < Nb; c++)
				s[r * 4 + c] = convertHexToShort(sBox[s[r * 4 + c]]);
		}
		return s;
	}

	private static short[] shiftRows(short[] s, int Nb)
	{ // shift row r of state S left by r bytes [§5.1.2]
		short[] t = new short[4];
		for (int r = 1; r < 4; r++)
		{
			for (int c = 0; c < 4; c++)
				t[c] = s[r * 4 + (c + r) % Nb]; // shift into temp copy
			for (int c = 0; c < 4; c++)
				s[r * 4 + c] = t[c]; // and copy back
		} // note that this will work for Nb=4,5,6, but not 7,8 (always 4 for
			// AES):
		return s; // see
					// asmaes.sourceforge.net/rijndael/rijndaelImplementation.pdf
	}

	private static short[] mixColumns(short[] s, int Nb)
	{ // combine bytes of each col of state S [§5.1.3]
		for (int c = 0; c < 4; c++)
		{
			short[] a = new short[4]; // 'a' is a copy of the current column
										// from 's'
			short[] b = new short[4]; // 'b' is a•{02} in GF(2^8)
			for (int i = 0; i < 4; i++)
			{
				a[i] = s[i * 4 + c];
				b[i] = ((s[i * 4 + c] & 0x80) > 0) ? (short) (s[i * 4 + c] << 1 ^ 0x011b)
						: (short) (s[i * 4 + c] << 1);

			}
			// a[n] ^ b[n] is a•{03} in GF(2^8)
			s[0 * 4 + c] = (short) (b[0] ^ a[1] ^ b[1] ^ a[2] ^ a[3]); // 2*a0 +
																		// 3*a1
																		// + a2
																		// + a3
			s[1 * 4 + c] = (short) (a[0] ^ b[1] ^ a[2] ^ b[2] ^ a[3]); // a0 *
																		// 2*a1
																		// +
																		// 3*a2
																		// + a3
			s[2 * 4 + c] = (short) (a[0] ^ a[1] ^ b[2] ^ a[3] ^ b[3]); // a0 +
																		// a1 +
																		// 2*a2
																		// +
																		// 3*a3
			s[3 * 4 + c] = (short) (a[0] ^ b[0] ^ a[1] ^ a[2] ^ b[3]); // 3*a0 +
																		// a1 +
																		// a2 +
																		// 2*a3
		}
		return s;
	}

	private static short[] slice(short[] state, int nBytes)
	{
		short[] sliced = new short[nBytes - 16];
		for (int i = 0; i < nBytes - 16; i++)
		{
			short s = 0;
			if (i < state.length)
			{
				s = state[i];
			}
			sliced[i] = s;
		}
		return sliced;
	}

	private static short[] concat(short[] state, short[] sliced)
	{
		short[] concated = new short[state.length + sliced.length];
		for (int i = 0; i < state.length + sliced.length; i++)
		{
			short s = 0;
			if (i < state.length)
			{
				s = state[i];
			}
			else if (i < state.length + sliced.length)
			{
				s = sliced[i - state.length];
			}
			concated[i] = s;
		}
		return concated;
	}
}
